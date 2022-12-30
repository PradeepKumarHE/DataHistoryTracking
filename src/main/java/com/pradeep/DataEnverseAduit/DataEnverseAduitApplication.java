package com.pradeep.DataEnverseAduit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.history.Revision;
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
	public List<CustomRevisionReponseDto> getRevisions(@PathVariable  int id){
		Revisions<Integer, Book> revisions=repository.findRevisions(id);
		List<Revision<Integer, Book>> dataList=revisions.getContent().stream().collect(Collectors.toList());
		List<CustomRevisionReponseDto> cList=new ArrayList<>();
		List<CustomRevisionReponseDto> cList1=dataList.stream().map(revision->new CustomRevisionReponseDto(revision.getEntity(),
				revision.getRequiredRevisionNumber(),
				revision.getMetadata().getRevisionType().toString(),
				revision.getMetadata().getRequiredRevisionInstant().toString()
				)).collect(Collectors.toList());
		return cList1;
	}
    @PostMapping("/revisions")
    public List<CustomRevisionReponseDto> getRevisionsByDate(@RequestBody CustomRevisionsRequestDto customRevisionsRequestDto){
		Revisions<Integer, Book> revisions=repository.findRevisions(customRevisionsRequestDto.getId());

		List<Revision<Integer, Book>> dataList=revisions.getContent().stream().
				filter(
						revision -> revision.getEntity().getCreatedDate().isAfter(customRevisionsRequestDto.getStartDate()) &&
								revision.getEntity().getCreatedDate().isBefore(customRevisionsRequestDto.getEndDate())
				).collect(Collectors.toList());
		List<CustomRevisionReponseDto> cList=new ArrayList<>();
		List<CustomRevisionReponseDto> cList1=dataList.stream().map(revision->new CustomRevisionReponseDto(revision.getEntity(),
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
