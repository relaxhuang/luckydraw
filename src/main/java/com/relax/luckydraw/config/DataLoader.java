package com.relax.luckydraw.config;

import com.relax.luckydraw.model.Prize;
import com.relax.luckydraw.repository.PrizeRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class DataLoader implements CommandLineRunner {
    private final PrizeRepository prizeRepository;

    public DataLoader(PrizeRepository prizeRepository) {
        this.prizeRepository = prizeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadPrizesFromCsv();
    }

    private void loadPrizesFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("prizes.csv");
            try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                // Skip header
                reader.readNext();
                
                String[] line;
                while ((line = reader.readNext()) != null) {
                    Prize prize = new Prize();
                    prize.setName(line[0]);
                    prize.setQuantity(Integer.parseInt(line[1]));
                    prize.setProbability(Double.parseDouble(line[2]));
                    prize.setDescription(line[3]);
                    prizeRepository.save(prize);
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to load prizes from CSV", e);
        }
    }
} 