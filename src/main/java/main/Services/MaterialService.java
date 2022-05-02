package main.Services;

import main.Entities.Material;
import main.Entities.User;
import main.Repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    UserService userService;

    @Transactional
    public void plusCountMaterials(User user, int materialNumber) {
        Material material;
        if (user.getMaterialCount(materialNumber) == 0) {
            material = new Material(user, materialNumber);
            addMaterial(material, user);
        } else {
            material = user.getMaterial(materialNumber);
            material.setMaterialCount(material.getMaterialCount() + 1);
        }

        materialRepository.save(material);
    }

    @Transactional
    public void minusCountMaterials(User user, int materialNumber) {
        Material material = user.getMaterial(materialNumber);
        switch (user.getMaterialCount(materialNumber)) {
            case 0:
                return;
            case 1: {
                deleteMaterialfromUser(material, user);
                break;
            }
            default: {
                material.setMaterialCount(material.getMaterialCount() - 1);
                materialRepository.save(material);
            }

        }


    }

    public void addMaterial(Material material, User user) {
        user.getList().add(material);
        saveMaterial(material);
    }

    @Transactional
    public void deleteMaterialfromUser(Material material, User user) {
        user.getList().remove(material);
        userService.resaveUser(user);
        deleteMaterial(material);
    }

    @Transactional
    public void saveMaterial(Material material) {
        materialRepository.save(material);
    }


    @Transactional
    public void deleteMaterial(Material material) {
        materialRepository.deleteById(material.getId());
    }

    @Transactional
    public void deleteMaterials(Set<Material> materialSet) {
        materialRepository.deleteAll(materialSet);
    }

    @Transactional
    public List<Material> GetMaterials(Set<Material> materialSet) {
        return materialRepository.findAll();
    }
}
