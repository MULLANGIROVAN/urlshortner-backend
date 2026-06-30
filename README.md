## 🧑‍💻 How to Use This Project

### 🔗 Live Application
Frontend: https://urlshortner-frontend-nu.vercel.app/

## 📂 GitHub Repository

🔗 Frontend Code: https://github.com/MULLANGIROVAN/urlshortner-frontend

### ⚙️ Backend API
The backend is hosted on Render and automatically connects to the Railway MySQL database.

⚠️ Note:  
Since Render uses a free tier, the first request may take up to **50 seconds** due to cold start.

---

## 🧪 How to Use

1. Open the frontend link above.
2. Enter a long URL in the input field.
3. (Optional) Add a custom short code.
4. Click **Shorten**.
5. You will receive a shortened URL.
6. Open the shortened link to redirect to the original URL.

---

## 🛠️ Tech Stack

- Frontend: React.js, Axios
- Backend: Spring Boot, Spring Security, JWT
- Database: MySQL (Railway)
- Deployment: Render (Backend), Vercel (Frontend)

---

## 📦 Local Setup (Optional)

If you want to run locally:

### Backend
```bash
mvn clean install
mvn spring-boot:run
