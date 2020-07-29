package com.company.taskerweb.entity;

import com.company.taskerweb.entity.Tasks.Tasks;
import com.haulmont.cuba.core.entity.BaseStringIdEntity;
import com.haulmont.cuba.core.entity.HasUuid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "TASKERWEB_SOME_DATA")
@Entity(name = "taskerweb_SomeData")
public class SomeData extends BaseStringIdEntity implements HasUuid {
    private static final long serialVersionUID = 1461493313631433097L;

    @Id
    @Column(name = "NAME", nullable = false, length = 10)
    protected String Name;

    @Column(name = "TASK_")
    protected String task;

    @Column(name = "UUID")
    protected UUID uuid;

    public void setTask(Tasks task) {
        this.task = task == null ? null : task.getId();
    }

    public Tasks getTask() {
        return task == null ? null : Tasks.fromId(task);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setId(String id) {
        this.Name = id;
    }

    @Override
    public String getId() {
        return Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}