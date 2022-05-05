package ai.giskard.security;

import ai.giskard.domain.Project;
import ai.giskard.repository.ProjectRepository;
import ai.giskard.service.ProjectService;
import ai.giskard.web.rest.errors.Entity;
import ai.giskard.web.rest.errors.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static ai.giskard.security.SecurityUtils.hasCurrentUserAnyOfAuthorities;

@Component(value = "permissionEvaluator")
@RequiredArgsConstructor
public class PermissionEvaluator {
    final ProjectRepository projectRepository;

    final ProjectService projectService;

    public boolean isCurrentUser(String login) throws Exception {
        return login.equals(SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new Exception(String.format("User %s not connected", login))));
    }

    /**
     * Determine if a user can write a project, i.e. is admin or project's owner
     *
     * @param id id of the project
     * @return true if the user can write
     */
    public boolean canWriteProject(@NotNull Long id) throws Exception {
        Project project = this.projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Entity.PROJECT, id));
        return (isCurrentUser(project.getOwner().getLogin()) || SecurityUtils.isAdmin());
    }

    /**
     * Determine if a user can write, ie has AICreator or Admin authorities
     */
    public boolean canWrite() {
        String[] writeAuthorities = {AuthoritiesConstants.AICREATOR, AuthoritiesConstants.ADMIN};
        return hasCurrentUserAnyOfAuthorities(writeAuthorities);
    }

    /**
     * Determine if the user can read the project, is admin, in project's guestlist or project's owner
     *
     * @param id project's id
     * @return true if user can read
     */
    public boolean canReadProject(@NotNull Long id) throws Exception {
        Project project = this.projectRepository.findOneWithGuestsById(id).orElseThrow(() -> new EntityNotFoundException(Entity.PROJECT, id));
        return (projectService.isUserInGuestList(project.getGuests()) || isCurrentUser(project.getOwner().getLogin()) || SecurityUtils.isAdmin());
    }

}