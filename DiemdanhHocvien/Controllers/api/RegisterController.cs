﻿using DiemdanhHocvien.DataAccess;
using DiemdanhHocvien.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Mail;
using System.Web.Http;
using System.Web.Security;

namespace DiemdanhHocvien.Controllers.api
{
    public class RegisterController : ApiController
    {
         
        // POST: api/Register
        public string Post([FromBody]RegistrationView registrationView)
        {

            bool statusRegistration = false;
            string messageRegistration = string.Empty;
             
                // Email Verification
                string userName = Membership.GetUserNameByEmail(registrationView.Email);
                if (!string.IsNullOrEmpty(userName))
                {
                    //ModelState.AddModelError("Warning Email", "Sorry: Email already Exists");
                    //return View(registrationView);
                    return JsonConvert.SerializeObject("Warning EmailSorry: Email already Exists");
                }
            //return JsonConvert.SerializeObject(userName);

            //Save User Data 
            using (AuthenticationDB dbContext = new AuthenticationDB())
                {
                    var user = new User()
                    {
                        Username = registrationView.Username,
                        FirstName = registrationView.FirstName,
                        LastName = registrationView.LastName,
                        Email = registrationView.Email,
                        Password = registrationView.Password,
                        ActivationCode = Guid.NewGuid(),
                    };

                registrationView.ActivationCode = user.ActivationCode;    

                    dbContext.Users.Add(user);
                    dbContext.SaveChanges();
                }

                //Verification Email
                VerificationEmail(registrationView.Email, registrationView.ActivationCode.ToString());
                messageRegistration = "Your account has been created successfully. ^_^";
                statusRegistration = true;
            
            //ViewBag.Message = messageRegistration;
            //ViewBag.Status = statusRegistration;

            //return View(registrationView);
            return JsonConvert.SerializeObject(messageRegistration+ statusRegistration);

        }
         

        [NonAction]
        public void VerificationEmail(string email, string activationCode)
        {
            var url = string.Format("/Account/ActivationAccount/{0}", activationCode);
            //var link = "link tuankhoi";
            var link = Request.RequestUri.AbsoluteUri.Replace(Request.RequestUri.PathAndQuery, url);


            var fromEmail = new MailAddress("ledangtuankhoi2@gmail.com", "Activation Account - AKKA");
            var toEmail = new MailAddress(email);

            var fromEmailPassword = "tuan khoi";
            string subject = "Activation Account !";

            string body = "<br/> Please click on the following link in order to activate your account" + "<br/><a href='" + link + "'> Activation Account ! </a>";

            var smtp = new SmtpClient
            {
                Host = "smtp.gmail.com",
                Port = 587,
                EnableSsl = true,
                DeliveryMethod = SmtpDeliveryMethod.Network,
                UseDefaultCredentials = false,
                Credentials = new NetworkCredential(fromEmail.Address, fromEmailPassword)
            };

            using (var message = new MailMessage(fromEmail, toEmail)
            {
                Subject = subject,
                Body = body,
                IsBodyHtml = true

            })

                smtp.Send(message);

        }
    }
}