<<<<<<< HEAD
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("registro-form");

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const nombre = form.nombre.value.trim();
    const apellido = form.apellido.value.trim();
    const dni = form.dni.value.trim();
    const direccion = form.direccion.value.trim();
    const email = form.email.value.trim();
    const password = form.password.value;
    const confirmPassword = form["confirm-password"].value;

    // Validación de contraseña
    if (password !== confirmPassword) {
      alert("Las contraseñas no coinciden.");
=======
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registro-form');
  const apiUrl = '/api/auth/register';

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const nombre    = document.getElementById('nombre').value.trim();
    const apellido  = document.getElementById('apellido').value.trim();
    const dni       = parseInt(document.getElementById('dni').value.trim());
    const direccion = document.getElementById('direccion').value.trim();
    const email     = document.getElementById('email').value.trim();
    const password  = document.getElementById('password').value;
    const confirm   = document.getElementById('confirm-password').value;

    if (password !== confirm) {
      alert('Las contraseñas no coinciden.');
>>>>>>> 99f4d3c (Version Funcional Spring Security)
      return;
    }

    const payload = {
      nombre,
      apellido,
      dni,
      direccion,
      email,
<<<<<<< HEAD
      password
    };

    try {
      const response = await fetch("/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
=======
      password,
      rol: "CLIENTE" // Fijamos rol por defecto al registrarse
    };

    try {
      const res = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
>>>>>>> 99f4d3c (Version Funcional Spring Security)
        },
        body: JSON.stringify(payload)
      });

<<<<<<< HEAD
      if (!response.ok) {
        const error = await response.json();
        throw new Error(error?.mensaje || "Error en el registro");
      }

      const data = await response.json();
      alert("Registro exitoso. ID de cliente: " + data.id);
      window.location.href = "login.html";
    } catch (error) {
      console.error("Error en el registro:", error);
      alert("No se pudo registrar: " + error.message);
=======
      if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || 'Error al registrar usuario.');
      }

      alert('Registro exitoso. Ahora puedes iniciar sesión.');
      window.location.href = '/login.html';

    } catch (err) {
      console.error('Registro fallido:', err);
      alert(`No se pudo registrar: ${err.message}`);
>>>>>>> 99f4d3c (Version Funcional Spring Security)
    }
  });
});
