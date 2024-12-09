// Escuchar el evento de cambio del select de cursos
document.getElementById("cursoSelect").addEventListener("change", function () {
  const cursoId = this.value; // Obtener el id del curso seleccionado
  const studentSelect = document.getElementById("studentSelect"); // Referencia al select de alumnos

  // Reiniciar el select de alumnos
  studentSelect.innerHTML = '<option value="">Seleccione un alumno</option>';

  // Si hay un curso seleccionado, realizar la solicitud al backend
  if (cursoId) {
    fetch(`/alumnos-por-curso/${cursoId}`) // Llamada al endpoint del backend
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Error en la solicitud: ${response.status}`);
        }
        return response.json(); // Convertir la respuesta a JSON
      })
      .then((data) => {
        // Iterar sobre los alumnos recibidos y agregar opciones al select
        data.forEach((alumno) => {
          const option = document.createElement("option");
          option.value = alumno.idusuario; // Asignar el ID del alumno como valor
          option.textContent = `${alumno.nombre} ${alumno.apellido}`; // Texto del alumno
          studentSelect.appendChild(option); // Añadir la opción al select
        });
      })
      .catch((error) => {
        alert(
          "Hubo un error al cargar los alumnos. Intente de nuevo más tarde."
        );
      });
  }
});

function cargarNotificaciones() {
  fetch(`/notificaciones-enviadas`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Error en la solicitud: ${response.status}`);
      }
      return response.json();
    })
    .then((data) => {
      const feedbackItems = document.getElementById("feedbackItems");
      feedbackItems.innerHTML = ""; // Limpiar la lista

      data.forEach((notificacion) => {
        // Crear un contenedor para la notificación
        const notificationDiv = document.createElement("div");
        notificationDiv.classList.add("updates", "fade-in");

        // Crear el contenido de la notificación
        notificationDiv.innerHTML = `
                    <div class="profile-photo1">
                        <img src="images/pic-3.jpg" alt="Perfil">
                    </div>
                    <div class="message">
                        <p><strong>Para:</strong> ${notificacion.destinatario}</p>
                        <p><strong>Mensaje:</strong> ${notificacion.contenido}</p>
                    </div>
                    <button class="delete-btn" data-id="${notificacion.idNotificacion}">Eliminar</button>
                `;

        // Agregar el contenedor al listado
        feedbackItems.appendChild(notificationDiv);
      });
    })
    .catch((error) => {
      alert("Hubo un error al cargar las notificaciones.");
    });
}

// Llama esta función cuando cargue la página
document.addEventListener("DOMContentLoaded", cargarNotificaciones);

document.addEventListener("DOMContentLoaded", () => {
  cargarNotificaciones();

  // Delegación de eventos para los botones "Eliminar"
  document
    .getElementById("feedbackItems")
    .addEventListener("click", (event) => {
      if (event.target.classList.contains("delete-btn")) {
        const notificacionId = event.target.dataset.id;

        // Confirmación antes de eliminar
        if (
          confirm("¿Estás seguro de que deseas eliminar esta notificación?")
        ) {
          eliminarNotificacion(notificacionId);
        }
      }
    });
});

function eliminarNotificacion(idNotificacion) {
  fetch(`/notificaciones/${idNotificacion}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(
          `Error al eliminar la notificación: ${response.status}`
        );
      }
      return response.text();
    })
    .then((message) => {
      alert(message); // Mostrar el mensaje del servidor
      cargarNotificaciones(); // Recargar la lista de notificaciones
    })
    .catch((error) => {
      alert("Hubo un error al intentar eliminar la notificación.");
    });
}
