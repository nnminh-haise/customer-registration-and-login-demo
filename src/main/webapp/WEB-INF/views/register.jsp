<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form:"%>

<!DOCTYPE html>
<html>
  <head>
    <title>Demo Customer login/registration</title>
  </head>
  <body>
    <h1>Registration</h1>
    <form action="register" method="post">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required /><br />
      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required /><br />
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required /><br />
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required /><br />
      <label for="phoneNumber">Phone Number:</label>
      <input type="tel" id="phoneNumber" name="phonenumber" required /><br />
      <label for="dateOfBirth">Date of Birth:</label>
      <input type="date" id="dateOfBirth" name="dob" required /><br />
      <button type="submit">Register</button>
    </form>
  </body>
</html>
