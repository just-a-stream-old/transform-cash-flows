package finance.modelling.data.transform.transformcashflows.repository.mapper;

import finance.modelling.fmcommons.data.schema.fmp.dto.FmpCashFlowDTO;
import finance.modelling.fmcommons.data.schema.fmp.dto.FmpCashFlowsDTO;
import finance.modelling.fmcommons.data.schema.model.CashFlow;
import finance.modelling.fmcommons.data.schema.model.CashFlows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CashFlowsMapper {
    CashFlowsMapper INSTANCE = Mappers.getMapper(CashFlowsMapper.class);

    CashFlows cashFlowsDTOToCashFlows(FmpCashFlowsDTO fmpCashFlowsDTO);

    @Mapping(source = "acceptedDate", target = "date")
    CashFlow cashFlowDTOToCashFlow(FmpCashFlowDTO fmpCashFlowDTO);
}
