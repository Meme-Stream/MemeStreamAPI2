package org.memestream.api.chefs;

import org.memestream.api.chefs.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, String> {
}
