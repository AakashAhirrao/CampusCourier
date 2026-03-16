import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Feed() {
  // 1. The Memory Box for our delivery requests
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();

  // 2. The Alarm Clock! (Runs automatically when the page loads)
  useEffect(() => {
    
    const fetchFeed = async () => {
      // Grab the ID card from the backpack
      const token = localStorage.getItem('jwt_token');

      // Security check: If they don't have a card, kick them back to the login page!
      if (!token) {
        navigate('/');
        return;
      }

      try {
        // Send the mail carrier to the Spring Boot bouncer
        const response = await fetch('http://localhost:8080/api/requests/feed', {
          method: 'GET',
          headers: {
            // Pin the ID card to the shirt! (Notice the space after 'Bearer ')
            'Authorization': `Bearer ${token}`, 
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {
          // If the bouncer lets us in, unpack the JSON data!
          const data = await response.json();
          setRequests(data); // Save the data into our memory box
        } else {
          // If the card is expired or fake, throw it away and kick them out
          console.log("Access denied by bouncer!");
          localStorage.removeItem('jwt_token');
          navigate('/');
        }
      } catch (error) {
        console.error("Network error:", error);
      }
    };

    fetchFeed(); // Turn the alarm clock on

  }, []); // <-- These empty brackets mean "Only run this ONE TIME when the page first opens"

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h2>Live Delivery Feed 📦</h2>
      
      {/* 3. The UI loop: Draw a box for every request in our memory box */}
      {requests.length === 0 ? (
        <p>No active requests right now. Be the first to order!</p>
      ) : (
        requests.map((req) => (
          <div key={req.id} style={{ border: '1px solid #ccc', padding: '15px', margin: '10px 0', borderRadius: '8px', backgroundColor: '#f9f9f9' }}>
            <h3 style={{ marginTop: 0 }}>{req.itemName}</h3>
            <p><strong>Details:</strong> {req.itemDescription}</p>
            <p><strong>Tip:</strong> ₹{req.tipAmount}</p>
            <p><strong>Status:</strong> {req.status}</p>
          </div>
        ))
      )}
      
      <button 
        onClick={() => {
          localStorage.removeItem('jwt_token');
          navigate('/');
        }} 
        style={{ marginTop: '20px', padding: '10px', backgroundColor: '#dc3545', color: 'white', border: 'none', cursor: 'pointer' }}>
        Logout
      </button>
    </div>
  );
}

export default Feed;