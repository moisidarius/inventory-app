package org.example.controller;

import org.example.model.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired private ProductRepository productRepository;
    @Autowired private TransactionRepository transactionRepository;

    @GetMapping
    public List<Product> getAll() { return productRepository.findAll(); }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String query) {
        // Trimitem același text și pentru nume și pentru SKU
        return productRepository.findByNumeContainingIgnoreCaseOrSkuContainingIgnoreCase(query, query);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() { return transactionRepository.findAllByOrderByDataDesc(); }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product p, @RequestHeader("Authorization") String auth) {
        if (!"darius-admin-123".equals(auth)) return ResponseEntity.status(403).build();
        Product saved = productRepository.save(p);

        Transaction t = new Transaction();
        t.setNumeProdus(saved.getNume());
        t.setCantitate(saved.getStoc());
        t.setTip("ADAUGARE");
        transactionRepository.save(t);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        if (!"darius-admin-123".equals(auth)) return ResponseEntity.status(403).build();
        productRepository.findById(id).ifPresent(p -> {
            Transaction t = new Transaction();
            t.setNumeProdus(p.getNume());
            t.setCantitate(p.getStoc());
            t.setTip("STERGERE");
            transactionRepository.save(t);
            productRepository.deleteById(id);
        });
        return ResponseEntity.ok().build();
    }
}