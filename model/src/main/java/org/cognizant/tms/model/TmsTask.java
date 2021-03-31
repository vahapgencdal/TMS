package org.cognizant.tms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TMS_TASK")
public class TmsTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TIME_SPENT", nullable = false)
    private int timeSpent;

    @Column(name = "TASK_ASSIGNEE", nullable = false)
    private String assignee;

    @Column(name = "TASK_STATUS", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "TASK_GROUP", nullable = false)
    private TaskGroup taskGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "id")
    private TmsTask parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<TmsTask> subTmsTasks;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = Boolean.TRUE;

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(LocalDateTime.now());
        setIsActive(Boolean.TRUE);
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

}