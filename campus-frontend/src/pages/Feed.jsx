import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Feed() {
  // 1. The Memory Box for our delivery requests
  const [requests, setRequests] = useState([]);
  const [itemName, setItemName] = useState('');
  const [itemDescription, setItemDescription] = useState('');
  const [tipAmount, setTipAmount] = useState('');
  const navigate = useNavigate();

  // 2. The Alarm Clock! (Runs automatically when the page loads)
  useEffect(() => {
    
    const fetchFeed = async () => {
      // Grab the ID card from the backpack
      const token = localStorage.getItem('jwt_token');

      console.log("The token being sent in the POST request is: ", token);

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

  const handleCreateRequest = async (e) => {

    e.preventDefault(); // Stops browser from refreshing
    const token = localStorage.getItem('jwt_token');

    try {
        const response = await fetch('http://localhost:8080/api/requests', {
            method: 'POST',
            headers: {
                'Authorization' : 'Bearer ' + token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                itemName: itemName,
                itemDescription: itemDescription,
                tipAmount: parseFloat(tipAmount)
            })
        });

        if (response.ok){
            // clear the form if successful
            setItemName('');
            setItemDescription('');
            setTipAmount('');

            window.location.reload(); // refresh the page to show new requests
        } else {
            console.log("Failed to create requests");
        }
    } catch (error) {
        console.log("Network error: ", error);
    }

  }

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h2>Live Delivery Feed 📦</h2>

      {/* --- NEW FORM SECTION --- */}
            <form onSubmit={handleCreateRequest} style={{ display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '30px', padding: '15px', backgroundColor: '#e9ecef', borderRadius: '8px' }}>
              <h3>Ask for a Delivery</h3>
              <input
                type="text"
                placeholder="What do you need? (e.g., Maggi, Coffee)"
                value={itemName}
                onChange={(e) => setItemName(e.target.value)}
                required
                style={{ padding: '8px' }}
              />
              <input
                type="text"
                placeholder="Where from? Any specific details?"
                value={itemDescription}
                onChange={(e) => setItemDescription(e.target.value)}
                required
                style={{ padding: '8px' }}
              />
              <input
                type="number"
                placeholder="Tip Amount (₹)"
                value={tipAmount}
                onChange={(e) => setTipAmount(e.target.value)}
                required
                style={{ padding: '8px' }}
              />
              <button type="submit" style={{ padding: '10px', backgroundColor: '#28a745', color: 'white', border: 'none', cursor: 'pointer', fontWeight: 'bold' }}>
                Post Request to Feed
              </button>
            </form>
            {/* --- END OF NEW FORM SECTION --- */}
      
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