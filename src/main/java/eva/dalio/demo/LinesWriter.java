package eva.dalio.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class LinesWriter implements ItemWriter<List<Allele>>, StepExecutionListener {

    private Logger logger = LoggerFactory.getLogger(LinesWriter.class);
    private FileUtils fileUtils;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        fileUtils =new FileUtils();
        logger.debug("Lines Writer initialized");
    }

    @Override
    public void write(List<? extends List<Allele>> lists) throws Exception {
        for (List<Allele> alleleList : lists) {
            for (Allele allele : alleleList) {
                fileUtils.write(allele);
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fileUtils.closeWriter();
        logger.debug("Line Writer ended.");
        return ExitStatus.COMPLETED;
    }


}
