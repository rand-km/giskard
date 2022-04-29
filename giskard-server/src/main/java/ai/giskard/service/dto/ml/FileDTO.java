package ai.giskard.service.dto.ml;

import ai.giskard.domain.ml.ProjectModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class FileDTO {
    @JsonProperty("id")
    @NotNull
    protected Long id;

    @JsonIgnore
    protected ProjectDTO project;

    @JsonProperty("file_name")
    protected String fileName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("creation_date")
    protected LocalDateTime createdOn;

    protected String location;

    public Long getSize() throws IOException {
        Path path = Paths.get(location);
        if (Files.exists(path)) {
            return Files.size(path);
        } else {
            return 0L;
        }
    }

    public Long getProjectId() {
        return project.getId();
    }

}