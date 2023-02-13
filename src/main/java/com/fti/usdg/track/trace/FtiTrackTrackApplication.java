package com.fti.usdg.track.trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fti.usdg.track.trace.common.FileStorageProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
    FileStorageProperties.class
})

public class FtiTrackTrackApplication {

	public static void main(String[] args) {
    SpringApplication.run(FtiTrackTrackApplication.class, args);
	}

}
