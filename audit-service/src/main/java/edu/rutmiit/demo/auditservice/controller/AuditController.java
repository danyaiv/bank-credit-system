package edu.rutmiit.demo.auditservice.controller;
import edu.rutmiit.demo.auditservice.model.AuditEntry;
import edu.rutmiit.demo.auditservice.storage.AuditStorage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditStorage auditStorage;

    public AuditController(AuditStorage auditStorage) {
        this.auditStorage = auditStorage;
    }


    @GetMapping
    public Map<String, Object> getAuditLog(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(required = false) String type) {

        List<AuditEntry> entries = (type == null)
                ? auditStorage.findLatest(limit)
                : auditStorage.findByType(type, limit);

        return Map.of(
                "system", "Recruitment Audit Service",
                "totalEntries", auditStorage.count(),
                "showing", entries.size(),
                "entries", entries
        );
    }


    @GetMapping("/vacancy/{isbn}")
    public List<AuditEntry> getVacancyHistory(@PathVariable String isbn) {
        return auditStorage.searchByDescription(isbn);
    }
}