package com.pradeep.DataEnverseAduit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@SpringBootApplication
@RestController
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class DataEnverseAduitApplication {

	@Autowired
	private BookRepository repository;

	@PostMapping("/saveBook")
	public Book saveBook(@RequestBody Book book) {
		return repository.save(book);
	}

	@PutMapping("/update/{id}/{pages}/{name}")
	public String updateBook(@PathVariable int id, @PathVariable int pages,@PathVariable String name) {
		Book book = repository.findById(id).get();
		book.setName(name);
		book.setPages(pages);
		repository.save(book);
		return "book updated";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		repository.deleteById(id);
		return "book deleted";
	}
	@GetMapping("/getInfo/{id}")
	public void getInfo(@PathVariable  int id){
		System.out.println(repository.findLastChangeRevision(id));
		System.out.println(repository.findRevisions(1));
	}

	@GetMapping("/revisions/{id}")
	public List<CustomRevision> getRevisions(@PathVariable  int id){
		Revisions<Integer, Book> revisions=repository.findRevisions(id);
		List<Revision<Integer, Book>> dataList=revisions.getContent().stream().collect(Collectors.toList());
		List<CustomRevision> cList=new ArrayList<>();
		List<CustomRevision> cList1=dataList.stream().map(revision->new CustomRevision(revision.getEntity(),
				revision.getRequiredRevisionNumber(),
				revision.getMetadata().getRevisionType().toString(),
				revision.getMetadata().getRequiredRevisionInstant().toString()
				)).collect(Collectors.toList());
		return cList1;
	}
    @GetMapping("/revisions/{id}/{startdate}/{enddate}")
    public List<CustomRevision> getRevisionsByDate(@PathVariable  int id,@PathVariable  Date startdate, @PathVariable Date enddate){
		Revisions<Integer, Book> revisions=repository.findRevisions(id);
		Instant s=startdate.toInstant();
		Instant e=enddate.toInstant();
		List<Revision<Integer, Book>> dataList=revisions.getContent().stream().
				filter(
				revision -> revision.getMetadata().getRequiredRevisionInstant().isAfter(s) &&
						revision.getMetadata().getRequiredRevisionInstant().isBefore(e)
					).collect(Collectors.toList());
		List<CustomRevision> cList=new ArrayList<>();
		List<CustomRevision> cList1=dataList.stream().map(revision->new CustomRevision(revision.getEntity(),
				revision.getRequiredRevisionNumber(),
				revision.getMetadata().getRevisionType().toString(),
				revision.getMetadata().getRequiredRevisionInstant().toString()
		)).collect(Collectors.toList());
		return cList1;
    }
	public static void main(String[] args) {
		SpringApplication.run(DataEnverseAduitApplication.class, args);
	}

}
