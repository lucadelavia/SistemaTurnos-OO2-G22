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
      return;
    }

    const payload = {
      nombre,
      apellido,
      dni,
      direccion,
      email,
      password,
      rol: "CLIENTE" // Fijamos rol por defecto al registrarse
    };

    try {
      const res = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || 'Error al registrar usuario.');
      }

      alert('Registro exitoso. Ahora puedes iniciar sesión.');
      window.location.href = '/login.html';

    } catch (err) {
      console.error('Registro fallido:', err);
      alert(`No se pudo registrar: ${err.message}`);
    }
  });
});
