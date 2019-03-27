package eva.dalio.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class LinesReader implements ItemReader<Line>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LinesReader.class);

    private FileUtils fileUtil;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fileUtil = new FileUtils("input/refsnp-chrMT.json");
        logger.debug("Line Reader initialized");
    }

    @Override
    public Line read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Line line = fileUtil.readLine();
        if (line != null) {
            logger.debug("Read line: " + line);
        }
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fileUtil.closeReader();
        logger.debug("Line Reader ended");
        return ExitStatus.COMPLETED;
    }
}
