document.addEventListener('DOMContentLoaded', () => {
  console.log("🟢 login.js cargado");

  const form = document.querySelector('form');
  const apiUrl = '/api/auth/login';

  form?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email')?.value.trim();
    const password = document.getElementById('password')?.value;

    if (!email || !password) {
      alert("Completá todos los campos.");
      return;
    }

    try {
      const res = await fetch(apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      console.log("📡 Estado de respuesta:", res.status);

      if (!res.ok) {
        const { error } = await res.json().catch(() => ({}));
        throw new Error(error || 'Credenciales inválidas');
      }

      const { token } = await res.json();

      // Guardar el token correctamente
      localStorage.setItem('authToken', token);
      console.log("✅ Token guardado en localStorage");

      // Redirigir al panel principal
      window.location.href = '/inicio';

    } catch (err) {
      console.error("❌ Error en login:", err.message);
      alert(err.message || "Error al iniciar sesión");
    }
  });
});
