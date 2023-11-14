package tn.moatez.project.services;

import tn.moatez.project.payload.request.EmailDetail;

public interface EmailService {
    Boolean sendSimpleMail(EmailDetail details) ;
}
