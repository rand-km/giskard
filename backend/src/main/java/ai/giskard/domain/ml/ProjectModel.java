package ai.giskard.domain.ml;

import ai.giskard.domain.AbstractAuditingEntity;
import ai.giskard.domain.Project;
import ai.giskard.utils.SimpleJSONStringAttributeConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity(name = "project_models")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel extends AbstractAuditingEntity {

    @Id
    @Column(length = 32)
    private String id;
    private String name;


    @ManyToOne
    @JsonBackReference
    private Project project;

    private long size;

    private String languageVersion;

    @Enumerated(EnumType.STRING)
    private ModelLanguage language;

    @Enumerated(EnumType.STRING)
    private ModelType modelType;

    private Float threshold;

    @Column(columnDefinition = "VARCHAR")
    @Convert(converter = SimpleJSONStringAttributeConverter.class)
    private List<String> featureNames;

    @Column(columnDefinition = "VARCHAR")
    @Convert(converter = SimpleJSONStringAttributeConverter.class)
    private List<String> classificationLabels;
}
