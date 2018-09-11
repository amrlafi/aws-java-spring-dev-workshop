package hello.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.mysql.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}