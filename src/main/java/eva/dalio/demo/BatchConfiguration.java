package eva.dalio.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableConfigurationProperties(Properties.class)
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<Line> itemReader() {
        return new LinesReader();
    }

    @Bean
    public ItemProcessor<Line, List<Allele>> itemProcessor() {
        return new LinesProcessor();
    }

    @Bean
    public ItemWriter<List<Allele>> itemWriter() {
        return new LinesWriter();
    }

    @Bean
    protected Step processLines(ItemReader<Line> reader, ItemProcessor<Line, List<Allele>> processor, ItemWriter<List<Allele>> writer) {
        System.out.println(properties.getChunkSize());
        return steps
                .get("processLines")
                .<Line, List<Allele>>chunk(properties.getChunkSize())
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job() {
        return jobs
                .get("pipeline-demo")
                .start(processLines(itemReader(), itemProcessor(), itemWriter()))
                .build();
    }

    @Bean
    public Properties property() {
        return new Properties();
    }

    public BatchConfiguration(Properties properties) {
        this.properties = properties;
    }

    private Properties properties;
}
