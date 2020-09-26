package finance.modelling.data.transform.transformcashflows.service;


import finance.modelling.fmcommons.data.schema.fmp.dto.FmpCashFlowsDTO;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.Consumer;

public interface CashFlowDataModelService {
    Consumer<KStream<String, FmpCashFlowsDTO>> generateCashFlowDataModel();
}
