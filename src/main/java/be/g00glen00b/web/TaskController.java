package be.g00glen00b.web;

import java.util.List;
import be.g00glen00b.dto.TaskDTO;
import be.g00glen00b.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskServiceImpl service;

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public TaskDTO findById(@RequestParam(value = "id") Integer id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean insert(@RequestBody TaskDTO task) {
        return service.insert(task);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public boolean update(@RequestBody TaskDTO task) {
        return service.update(task);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestParam("id") int id) {
        return service.delete(id);
    }

    @RequestMapping(value = "/cache", method = RequestMethod.DELETE)
    public void clearCache() {
        service.clearCache();
    }
}
