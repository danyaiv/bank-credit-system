package edu.rutmiit.demo.auditservice.storage;

import edu.rutmiit.demo.auditservice.model.AuditEntry;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Component
public class AuditStorage {


    private final AtomicLong counter = new AtomicLong(0);

    private final Map<Long, AuditEntry> entries = new ConcurrentHashMap<>();

    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    public AuditEntry save(AuditEntry entry) {
        long id = counter.incrementAndGet();
        AuditEntry recordWithId = new AuditEntry(
                id,
                entry.eventId(),
                entry.eventType(),
                entry.source(),
                entry.eventTimestamp(),
                entry.receivedAt(),
                entry.description()
        );
        entries.put(id, recordWithId);
        processedEventIds.add(entry.eventId());
        return recordWithId;
    }


    public boolean isDuplicate(String eventId) {
        return processedEventIds.contains(eventId);
    }


    public List<AuditEntry> findLatest(int limit) {
        return entries.values().stream()
                .sorted(Comparator.comparingLong(AuditEntry::sequenceNumber).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }


    public List<AuditEntry> findByType(String type, int limit) {
        return entries.values().stream()
                .filter(e -> e.eventType().equalsIgnoreCase(type))
                .sorted(Comparator.comparingLong(AuditEntry::sequenceNumber).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }


    public List<AuditEntry> searchByDescription(String query) {
        return entries.values().stream()
                .filter(e -> e.description().contains(query))
                .sorted(Comparator.comparingLong(AuditEntry::sequenceNumber).reversed())
                .toList();
    }

    public long count() {
        return entries.size();
    }
}