package be.g00glen00b.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import be.g00glen00b.dto.TaskDTO;
import com.sun.javafx.tk.Toolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    ArrayList<TaskDTO> objs = new ArrayList();

    @Cacheable(value = "tasks")
    public ArrayList<TaskDTO> findAll() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Retrieving tasks");
        createTasks();
        return objs;
    }

    private void createTasks() {
        logger.info("Creating tasks");
        objs.add(new TaskDTO(1L, "My first task", true));
        objs.add(new TaskDTO(2L, "My second task", false));
    }

    @CachePut(value = "tasks", key = "#task.id")
    public boolean insert(TaskDTO task) {
        logger.info("inserting task");
        objs.add(task);
        return true;
    }

    @CachePut(value = "tasks", key = "#task.id")
    public boolean update(TaskDTO task) {

        for(TaskDTO t : objs){
            if(t.getId() == task.getId()){
                t.setCompleted(task.isCompleted());
                t.setId(task.getId());
                t.setTask(task.getTask());
                return true;
            }
        }
        return false;
    }

    @CacheEvict(value = "tasks", key = "#id")
    public boolean delete(int id) {

        for(int i = 0; i < objs.size(); i++){
            if(id == objs.get(i).getId()){
                objs.remove(i);
                return true;
            }
        }

        return false;
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskDTO findById(int id) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("finding task by id");
        for (TaskDTO t : objs){
            if (t.getId() == id)
                return t;
        }
        return null;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void clearCache() {
        logger.info("clearing cache");
    }
}
