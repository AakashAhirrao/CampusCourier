import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

function Register() {
  // 1. Memory Boxes for all the student details
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [hostelBlock, setHostelBlock] = useState('');
  const [roomNumber, setRoomNumber] = useState('');

  const navigate = useNavigate();

  // 2. The Action: Send the new student to Spring Boot
  const handleRegister = async (e) => {
    e.preventDefault();
    console.log("Sending registration data...");

    try {
      // Send the mail carrier to your Spring Boot auth controller
      const response = await fetch(`http://localhost:8080/api/users/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: password,
          hostelBlock: hostelBlock,
          roomNumber: roomNumber
        }),
      });

      if (response.ok) {
        console.log("Registration successful!");
        // Steer the user back to the login page so they can sign in!
        navigate('/');
      } else {
        console.log("Registration failed. The email might already exist.");
      }
    } catch (error) {
      console.error("Network error:", error);
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <h2>Join CampusCourier 🎓</h2>
      <p>Create your student account below.</p>

      <form onSubmit={handleRegister} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>

        <div style={{ display: 'flex', gap: '10px' }}>
          <input type="text" placeholder="First Name" value={firstName} onChange={(e) => setFirstName(e.target.value)} required style={{ padding: '10px', width: '100%' }} />
          <input type="text" placeholder="Last Name" value={lastName} onChange={(e) => setLastName(e.target.value)} required style={{ padding: '10px', width: '100%' }} />
        </div>

        <input type="email" placeholder="College Email" value={email} onChange={(e) => setEmail(e.target.value)} required style={{ padding: '10px' }} />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required style={{ padding: '10px' }} />

        <div style={{ display: 'flex', gap: '10px' }}>
          <input type="text" placeholder="Hostel Block (e.g., A)" value={hostelBlock} onChange={(e) => setHostelBlock(e.target.value)} required style={{ padding: '10px', width: '100%' }} />
          <input type="text" placeholder="Room Number" value={roomNumber} onChange={(e) => setRoomNumber(e.target.value)} required style={{ padding: '10px', width: '100%' }} />
        </div>

        <button type="submit" style={{ padding: '10px', backgroundColor: '#28a745', color: 'white', border: 'none', cursor: 'pointer', fontWeight: 'bold' }}>
          Sign Up
        </button>
      </form>

      <p style={{ marginTop: '20px', textAlign: 'center' }}>
        Already have an account? <Link to="/">Login here</Link>
      </p>
    </div>
  );
}

export default Register;