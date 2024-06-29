package com.arekproject.studiaprojekt.controller;

import com.arekproject.studiaprojekt.entity.Notes;
import com.arekproject.studiaprojekt.entity.UserDtls;
import com.arekproject.studiaprojekt.repository.NotesRepository;
import com.arekproject.studiaprojekt.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotesRepository notesRepository;
    @ModelAttribute
    public void addCommnData(Principal p, org.springframework.ui.Model m) {
        String email = p.getName();
        UserDtls user = userRepository.findByEmail(email);
        m.addAttribute("user", user);
    }

    @GetMapping("/addNotes")
    public String home() {
        return "user/add_notes";
    }

    @GetMapping("/viewNotes/{page}")
    public String viewNotes(@PathVariable int page, org.springframework.ui.Model m,Principal p) {

        String email = p.getName();
        UserDtls user = userRepository.findByEmail(email);


        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Notes> notes = notesRepository.findByNotesByUser(user.getId(), pageable);

        m.addAttribute("pageNo", page);
        m.addAttribute("totalPage", notes.getTotalPages());
        m.addAttribute("Notes", notes);
        m.addAttribute("totalElement", notes.getTotalElements());

        return "user/view_notes";
    }

    @GetMapping("/editNotes/{id}")
    public String editNotes(@PathVariable int id,org.springframework.ui.Model m) {
        Optional<Notes> n = notesRepository.findById(id);
        if (n.isPresent()) {
            Notes notes = n.get();
            m.addAttribute("notes", notes);
        }
        return "user/edit_notes";
    }

    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal p) {
        String email = p.getName();
        UserDtls user = userRepository.findByEmail(email);

        notes.setUserDtls(user);
        Notes updateNotes = notesRepository.save(notes);
        if (updateNotes != null) {
            session.setAttribute("msg", "Notes Update Successfully");
        } else {
            session.setAttribute("msg", "Something wrong on server");
        }
        System.out.println(notes);
        return "redirect:/user/viewNotes/0";
    }

    @GetMapping("/deleteNotes/{id}")
    public String deleteNotes(@PathVariable int id, HttpSession session) {
        Optional<Notes> notes = notesRepository.findById(id);
        if (notes != null) {
            notesRepository.delete(notes.get());
            session.setAttribute("msg", "Notes Delete Successfully");
        }
        return "redirect:/user/viewNotes/0";
    }



    @GetMapping("/viewProfile")
    public String viewProfile(){
        return"user/view_profile";
    }

    @PostMapping("/saveNotes")
    public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p) {
            String email = p.getName();
            UserDtls u = userRepository.findByEmail(email);
            notes.setUserDtls(u);

            Notes n = notesRepository.save(notes);

            if(n!=null){
                session.setAttribute("msg", "Notes Added Succesfully");
            }else{
                session.setAttribute("msg", "Something wrong on server");
            }

            System.out.println(notes);
            return "redirect:/user/addNotes";
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute UserDtls user, HttpSession session, Model m){
        Optional<UserDtls> Olduser = userRepository.findById(user.getId());

        if(Olduser!=null){
            user.setPassword(Olduser.get().getPassword());
            user.setRole(Olduser.get().getRole());
            user.setEmail(Olduser.get().getEmail());

            UserDtls updateUser = userRepository.save(user);
            if(updateUser!=null){
                m.addAttribute("user", updateUser);
                session.setAttribute("msg", "Profile Update Successfully");
            }
        }
        return "redirect:/user/viewProfile";
    }

}
