package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserController.REST_URL;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
                    // it's fake error
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  ProfileRepository profileRepository;
    @Test
    public void getWithRealUser() throws Exception {
        User user = userRepository.findById(1L).get();
        AuthUser authUser = new AuthUser(user);
        mvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(user(authUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    @Test
    public void getWithUnRealUser() throws Exception {
        User user = new User(100L,"12345678@gmail.com","dgdtg043","123","321","33322211");
        AuthUser authUser = new AuthUser(user);
        mvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(user(authUser)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

//    @Test
//    public void updateProfileWithRealUser() throws Exception {
//        User user = userRepository.findById(1L).get();
//        Profile profile = profileRepository.findById(1L).get();
//        AuthUser authUser = new AuthUser(user);
//        mvc.perform(MockMvcRequestBuilders.put(REST_URL))
//    }

}
