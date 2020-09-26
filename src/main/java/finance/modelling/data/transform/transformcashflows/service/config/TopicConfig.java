package finance.modelling.data.transform.transformcashflows.service.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TopicConfig {

    private final String traceIdHeaderName;
    private final String ingestFmpCashFlowTopic;

    public TopicConfig(
            @Value("${spring.cloud.stream.kafka.streams.header.traceId}") String traceIdHeaderName,
            @Value("${spring.cloud.stream.bindings.generateCashFlowDataModel-in-0.destination}") String ingestFmpCashFlowTopic) {
        this.traceIdHeaderName = traceIdHeaderName;
        this.ingestFmpCashFlowTopic = ingestFmpCashFlowTopic;
    }
}
