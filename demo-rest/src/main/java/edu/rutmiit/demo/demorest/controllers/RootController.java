package edu.rutmiit.demo.demorest.controllers;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping
    public ResponseEntity<RepresentationModel<?>> getRoot() {
        RepresentationModel<?> root = new RepresentationModel<>();
        root.add(linkTo(methodOn(RootController.class).getRoot()).withSelfRel());
        root.add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("clients"));
        root.add(linkTo(methodOn(LoanApplicationController.class).getAllLoans()).withRel("loans"));
        return ResponseEntity.ok(root);
    }
}