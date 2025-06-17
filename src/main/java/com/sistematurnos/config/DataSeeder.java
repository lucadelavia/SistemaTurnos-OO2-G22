package com.sistematurnos.config;

import com.sistematurnos.entity.*;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IDiasDeAtencionService diaService;
    private final IEspecialidadService especialidadService;
    private final IServicioService servicioService;
    private final IEmpleadoService empleadoService;
    private final IClienteService clienteService;
    private final IEstablecimientoService establecimientoService;
    private final ISucursalService sucursalService;
    private final ITurnoService turnoService;
    private final IUsuarioService usuarioService;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            // Verificar si ya existen datos en la base de datos
            if (shouldSeedData()) {
                seedData();
                System.out.println("✅ Datos iniciales cargados exitosamente");
            } else {
                System.out.println("⏩ La base de datos ya contiene datos. No se cargarán datos iniciales.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error durante la carga de datos iniciales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean shouldSeedData() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRangoFechas(
                    LocalDateTime.now().minusYears(1).toLocalDate(),
                    LocalDateTime.now().toLocalDate());

            List<Servicio> servicios = servicioService.traerServicios();

            return usuarios.isEmpty() || servicios.isEmpty();
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo verificar si existen usuarios. Se procederá con el seeding. Motivo: " + e.getMessage());
            return true;
        }
    }

    private void seedData() {
        // Días de atención
        DiasDeAtencion lunes = diaService.altaDiaDeAtencion("Lunes");
        DiasDeAtencion martes = diaService.altaDiaDeAtencion("Martes");
        DiasDeAtencion miercoles = diaService.altaDiaDeAtencion("Miércoles");
        DiasDeAtencion jueves = diaService.altaDiaDeAtencion("Jueves");
        DiasDeAtencion viernes = diaService.altaDiaDeAtencion("Viernes");

        // Especialidades
        Especialidad arquitectura = especialidadService.altaEspecialidad("Arquitectura de Software");
        Especialidad programacion = especialidadService.altaEspecialidad("Programación Avanzada");
        Especialidad bd = especialidadService.altaEspecialidad("Base de Datos");

        // Servicios
        Servicio servArquitectura = servicioService.altaServicio("Arquitectura de Software", 90, arquitectura.getId());
        Servicio servProgramacion = servicioService.altaServicio("Programación Avanzada", 90, programacion.getId());
        Servicio servBD = servicioService.altaServicio("Base de Datos", 90, bd.getId());

        // Empleados (Profesores)
        Empleado profAlejandra = new Empleado(
                "Alejandra", "Vranic", "alejandravranic@unla.edu.ar", "alejandra123",
                "29 de Septiembre 3901", 12345678, true, LocalDateTime.now(), Rol.EMPLEADO,
                201L, "MAT-001");
        profAlejandra = empleadoService.altaEmpleado(profAlejandra);
        empleadoService.asignarEspecialidad(profAlejandra.getId(), arquitectura);

        Empleado profOscar = new Empleado(
                "Oscar", "Ruina", "oscarruina@unla.edu.ar", "oscar123",
                "29 de Septiembre 3901", 23456789, true, LocalDateTime.now(), Rol.EMPLEADO,
                202L, "MAT-002");
        profOscar = empleadoService.altaEmpleado(profOscar);
        empleadoService.asignarEspecialidad(profOscar.getId(), programacion);

        Empleado profGustavo = new Empleado(
                "Gustavo", "Siciliano", "gustavosiciliano@unla.edu.ar", "gustavo123",
                "29 de Septiembre 3901", 34567891, true, LocalDateTime.now(), Rol.EMPLEADO,
                203L, "MAT-003");
        profGustavo = empleadoService.altaEmpleado(profGustavo);
        empleadoService.asignarEspecialidad(profGustavo.getId(), bd);

        // Clientes (Alumnos)
        Cliente cliValentina = clienteService.altaCliente(
                "Valentina", "Mohamed", "mohamedvalentinab@alumno.unla.edu.ar",
                "29 de Septiembre 3901", 46267488, "valentina123");

        Cliente cliLuca = clienteService.altaCliente(
                "Luca", "De La Via", "lucadelavia@alumno.unla.edu.ar",
                "29 de Septiembre 3901", 43904770, "luca123");

        Cliente cliSebastian = clienteService.altaCliente(
                "Sebastian", "Sdrubolini", "svsdrubolini@alumno.unla.edu.ar",
                "29 de Septiembre 3901", 40945930, "sebastian123");

        // Establecimiento UNLa
        Establecimiento unla = establecimientoService.altaEstablecimiento(
                "UNLa - Universidad Nacional de Lanús",
                "30-50123456-7",
                "29 de Septiembre 3901, Lanús",
                "Universidad pública argentina ubicada en el sur del Gran Buenos Aires");

        // Sucursal (Aula Magna)
        Sucursal aulaMagnus = new Sucursal();
        aulaMagnus.setDireccion("Aula Magna - Edificio José Hernández");
        aulaMagnus.setEspacio(50);
        aulaMagnus.setEstado(true);
        aulaMagnus.setTelefono("1142421234");
        aulaMagnus.setHoraApertura(LocalTime.of(8, 0));
        aulaMagnus.setHoraCierre(LocalTime.of(22, 0));
        aulaMagnus.setEstablecimiento(unla);
        aulaMagnus.setLstDiasDeAtencion(Set.of(lunes, martes, miercoles, jueves, viernes));
        aulaMagnus = sucursalService.guardar(aulaMagnus);

        aulaMagnus.setLstEspecialidad(new HashSet<>(List.of(arquitectura, programacion, bd)));

        // Turnos
        turnoService.altaTurno(
                LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
                true,
                "TURNO-001",
                servArquitectura,
                cliValentina,
                profAlejandra,
                aulaMagnus);

        turnoService.altaTurno(
                LocalDateTime.now().plusDays(2).withHour(11).withMinute(0),
                true,
                "TURNO-002",
                servProgramacion,
                cliLuca,
                profOscar,
                aulaMagnus);

        turnoService.altaTurno(
                LocalDateTime.now().plusDays(3).withHour(14).withMinute(0),
                true,
                "TURNO-003",
                servBD,
                cliSebastian,
                profGustavo,
                aulaMagnus);

        // Admin
        usuarioService.altaUsuario(
                "Admin", "Sistema", "admin@unla.edu.ar", "admin123",
                "Rectorado UNLa", 99999999, Rol.ADMIN);
    }
}