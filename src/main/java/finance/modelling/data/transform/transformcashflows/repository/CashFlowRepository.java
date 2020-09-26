package finance.modelling.data.transform.transformcashflows.repository;

import finance.modelling.fmcommons.data.schema.model.CashFlows;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CashFlowRepository extends ReactiveMongoRepository<CashFlows, UUID> {
}
