package eva.dalio.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinesProcessor implements ItemProcessor<Line, List<Allele>>, StepExecutionListener {

    private Logger logger = LoggerFactory.getLogger(LinesProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Line Processor initialized");
    }

    @Override
    public List<Allele> process(Line line) throws Exception {

        List<Allele> variants = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(line.getValue());
        Iterator<JsonNode> items = root
                .get("primary_snapshot_data")
                .get("placements_with_allele")
                .elements();

        while (items.hasNext()) {
            JsonNode node = items.next();
            Iterator<JsonNode> alleles = node.get("alleles").elements();
            while (alleles.hasNext()) {
                JsonNode allele = alleles.next().get("allele").get("spdi");
                if (allele != null && allele.has("seq_id") && allele.has("position")) {
                    Allele alleleObj = new Allele(
                            allele.get("seq_id").asText(),
                            allele.get("position").asLong(),
                            allele.get("deleted_sequence").asText(),
                            allele.get("inserted_sequence").asText()
                    );
                    if (alleleObj.isVariant()) {
                        variants.add(alleleObj);
                    }
                }
            }
        }

        return variants;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Line Processor ended");
        return ExitStatus.COMPLETED;
    }


}
