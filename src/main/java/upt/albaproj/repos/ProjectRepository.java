package upt.albaproj.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import upt.albaproj.entities.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByName(String name);
}