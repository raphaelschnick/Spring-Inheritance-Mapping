package de.sybit.spring.wheel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WheelRepository extends JpaRepository<Wheel, Long> {
}
