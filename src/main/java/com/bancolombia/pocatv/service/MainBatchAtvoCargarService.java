package com.bancolombia.pocatv.service;

public interface MainBatchAtvoCargarService {
	void executeBatch();
	boolean isAlreadyExecutedToday(String jobName);
	void sendUserMessage(String message);
	void clearFiles();
	void finalClearFiles();
}
