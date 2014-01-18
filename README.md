# clj-rapportive

A Clojure terminal program to fetch information about a user from Rapportive.

## Usage
    java -jar clj-rapportive-0.1.0-SNAPSHOT-standalone.jar <rapportive-useremail> <email>

_rapportive-useremail_ is the email of a rapportive user.
_email_ is the email of the person you want rapportive details about.

## API Usage
    (get-contact-info (get-session-token "<rapportive-useremail>") "<email>")

## Compilation
    //Change directory to the project
    lein deps
    lein compile
    lein uberjar

## Notes

The program was developed based on this blog [link] (http://jordan-wright.github.io/blog/2013/10/14/automated-social-engineering-recon-using-rapportive/) by Jordan Wright.

_Random emails to fetch the session token as mentioned by the blog post don't work anymore. They return HTTP error code 429 - Too many simultaneous connections._

