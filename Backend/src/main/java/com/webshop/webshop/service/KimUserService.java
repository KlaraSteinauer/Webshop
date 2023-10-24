package com.webshop.webshop.service;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.security.KimUserDetails;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KimUserService implements UserDetailsService {

    @Autowired
    KimUserRepository kimUserRepository;

    /**
     * Saves a User.
     *
     * @param kimUserDTO DTO holding User information
     * @return
     */
    public KimUser save(KimUserDTO kimUserDTO) {
        try {
            return kimUserRepository.save(kimUserDTO.convertToKimUser());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid fields for user!");
        }
    }

    /**
     * Fetches User from the Database.
     *
     * @param id user id
     * @return KimUser
     */
    public KimUser findById(Long id) {
        var kimUser = kimUserRepository.findById(id);
        if (kimUser.isEmpty()) {
            throw new ObjectNotFoundException(kimUser, "User not found.");
        }
        return kimUser.get();
    }

    /**
     * Fetches User from the Database using userName.
     *
     * @param userName user name
     * @return KimUser
     */
    public KimUser findByUserName(String userName) {
        var kimUser = kimUserRepository.findByUserName(userName);
        if (kimUser.isEmpty()) {
            throw new ObjectNotFoundException(kimUser, "User not found.");
        }
        return kimUser.get();
    }

    /**
     * Fetches all Users from the Database.
     *
     * @return List of all Users.
     */
    public List<KimUser> findAll() {
        var allUsers = kimUserRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new ObjectNotFoundException(allUsers, "No Users found.");
        }
        return allUsers;

    }

    /**
     * Updates a User.
     *
     * @param updateKimUserDTO DTO holding information of updated User
     * @param id id of user to update
     * @return KimUser
     */
    public KimUserDTO update(Long id, KimUserDTO updateKimUserDTO) throws ObjectNotFoundException {
        KimUser user = findById(id);
        user.setUserName(updateKimUserDTO.getUserName());
        user.setUserPassword(updateKimUserDTO.getUserPassword());
        user.setUserEmail(updateKimUserDTO.getUserEmail());
        user.setGender(updateKimUserDTO.getGender());
        user.setFirstName(updateKimUserDTO.getFirstName());
        user.setLastName(updateKimUserDTO.getLastName());
        kimUserRepository.save(user);
        return user.convertToDto();
    }

    /**
     * Removes a User from the Database.
     *
     * @param id user id
     */
    public void deleteById(Long id) {
        try {
            kimUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(Product.class, "User with id: " + id + "not found!");
        }
    }

    /**
     * Activates a User.
     *
     * @param userId user id
     * @return true if activity has been changed
     *          / false otherwise
     */
    public boolean activateUser(Long userId) {
        KimUser user = findById(userId);
        boolean activityHasChanged = false;
        if (!user.isActive()) {
            user.setActive(true);
            kimUserRepository.save(user);
            activityHasChanged = true;
        }
        return activityHasChanged;
    }

    /**
     * Deactivates a User.
     *
     * @param userId user id
     * @return true if activity has been changed
     *          / false otherwise
     */
    public boolean deactivateUser(Long userId) {
        KimUser user = findById(userId);
        boolean activityHasChanged = false;
        if (user.isActive()) {
            user.setActive(false);
            kimUserRepository.save(user);
            activityHasChanged = true;
        }
        return activityHasChanged;
    }

    /**
     * Creates UserDetails from User information.
     *
     * @param username user name
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            KimUser user = kimUserRepository.findByUserName(username).get();
            return new KimUserDetails(
                    user.getId(), user.getUserName(), user.getUserPassword(), user.getRole());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User name doesn't exist!");
        }

    }
}
