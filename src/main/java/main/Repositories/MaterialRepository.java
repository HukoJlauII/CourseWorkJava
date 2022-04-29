package main.Repositories;

import main.Entities.Material;
import main.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Integer> {
    Material findMaterialByUserIDAndMaterialNumber(User userID, int materialNumber);
    List<Material> findByUserID(User userID);
}
