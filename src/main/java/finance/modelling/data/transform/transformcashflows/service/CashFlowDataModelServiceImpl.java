package finance.modelling.data.transform.transformcashflows.service;

import finance.modelling.data.transform.transformcashflows.repository.CashFlowRepository;
import finance.modelling.data.transform.transformcashflows.repository.config.MongoDbConfig;
import finance.modelling.data.transform.transformcashflows.repository.mapper.CashFlowsMapper;
import finance.modelling.data.transform.transformcashflows.service.config.TopicConfig;
import finance.modelling.fmcommons.data.logging.LogDb;
import finance.modelling.fmcommons.data.logging.kstream.LogMessageConsumed;
import finance.modelling.fmcommons.data.schema.fmp.dto.FmpBalanceSheetsDTO;
import finance.modelling.fmcommons.data.schema.fmp.dto.FmpCashFlowsDTO;
import finance.modelling.fmcommons.data.schema.model.CashFlows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Service
public class CashFlowDataModelServiceImpl implements CashFlowDataModelService {

    private final TopicConfig topics;
    private final CashFlowRepository cashFlowRepository;
    private final MongoDbConfig dbConfig;

    public CashFlowDataModelServiceImpl(
            TopicConfig topics,
            CashFlowRepository cashFlowRepository,
            MongoDbConfig dbConfig) {
        this.topics = topics;
        this.cashFlowRepository = cashFlowRepository;
        this.dbConfig = dbConfig;
    }

    @Bean
    public Consumer<KStream<String, FmpCashFlowsDTO>> generateCashFlowDataModel() {
        return fmpBalanceSheets -> fmpBalanceSheets
                .transformValues(() -> new LogMessageConsumed<>(topics.getTraceIdHeaderName()))
                .mapValues(CashFlowsMapper.INSTANCE::cashFlowsDTOToCashFlows)
                .foreach(this::saveToCashFlowRepository);
    }

    protected void saveToCashFlowRepository(String _key, CashFlows cashFlows) {
        Mono
                .just(cashFlows)
                .flatMap(cashFlowRepository::save)
                .subscribe(
                        cashFlowsMono -> LogDb.logInfoDataItemSaved(
                                CashFlows.class, cashFlowsMono.getSymbol(), dbConfig.getDbUri()),
                        error -> LogDb.logErrorFailedDataItemSave(
                                CashFlows.class, error, dbConfig.getDbUri(), List.of("Log and fail"))
                );
    }
}
