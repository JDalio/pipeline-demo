package eva.dalio.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class PipelineDemoTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Test
    public void pipelineTest()
            throws Exception {

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}
