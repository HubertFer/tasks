package br.com.udemy.tasks.repository;

import br.com.udemy.tasks.model.Task;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TaskCustomRepository {

    private final MongoOperations mongoOperations;


    public TaskCustomRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Page<Task> findPaginated(Task task, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by ("title").ascending());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("priority", "state");

        Example<Task> example = Example.of(task, matcher);

        Query query = Query.query(Criteria.byExample(example)).with(pageable);

        if(task.getPriority() > 0) {
            query.addCriteria(Criteria.where("priority").is(task.getPriority()));
        }

        if(task.getState() != null) {
            query.addCriteria(Criteria.where("state").is(task.getState()));
        }

        return PageableExecutionUtils.getPage(
                mongoOperations.find(query, Task.class), pageable
                , () -> mongoOperations.count(query, Task.class));
    }
}
