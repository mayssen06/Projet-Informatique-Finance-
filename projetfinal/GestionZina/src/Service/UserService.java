package tn.esprit.GestionZina.marchefinancier.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.GestionZina.marchefinancier.Entites.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tn.esprit.GestionZina.marchefinancier.Repositories.IUserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUser {
    private final IUserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String s) {
                return userRepository.findByEmail(s).orElseThrow(() -> new RuntimeException("User not found"));
            }
        };
    }
    public int demandeSolde(int id) {
        User user=userRepository.findById(id).get();
        user.setCredit(user.getCredit() + 1);
        if (user.getCredit()<=2) {
            user.setDemandeSolde(25000);
            user.setSolde(user.getSolde()+user.getDemandeSolde());
            userRepository.save(user);
        }
        return user.getCredit();
    }
}
