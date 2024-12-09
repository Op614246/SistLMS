// Escuchar el evento de cambio del select de cursos
document.getElementById("cursoSelect").addEventListener("change", function () {
  const cursoId = this.value; // Obtener el id del curso seleccionado
  const studentSelect = document.getElementById("studentSelect"); // Referencia al select de alumnos

  // Reiniciar el select de alumnos
  studentSelect.innerHTML = '<option value="">Seleccione un alumno</option>';

  // Si hay un curso seleccionado, realizar la solicitud al backend
  if (cursoId) {
      fetch(`/alumnos-por-curso/${cursoId}`) // Llamada al endpoint del backend
          .then(response => {
              if (!response.ok) {
                  throw new Error(`Error en la solicitud: ${response.status}`);
              }
              return response.json(); // Convertir la respuesta a JSON
          })
          .then(data => {
              // Iterar sobre los alumnos recibidos y agregar opciones al select
              data.forEach(alumno => {
                  const option = document.createElement("option");
                  option.value = alumno.idusuario; // Asignar el ID del alumno como valor
                  option.textContent = `${alumno.nombre} ${alumno.apellido}`; // Texto del alumno
                  studentSelect.appendChild(option); // Añadir la opción al select
              });
          })
          .catch(error => {
              alert("Hubo un error al cargar los alumnos. Intente de nuevo más tarde.");
          });
  }
});

function cargarNotificaciones() {
  fetch(`/notificaciones-enviadas`)
      .then(response => {
          if (!response.ok) {
              throw new Error(`Error en la solicitud: ${response.status}`);
          }
          return response.json();
      })
      .then(data => {
          const feedbackList = document.getElementById("feedbackItems");
          feedbackList.innerHTML = ""; // Limpiar la lista

          data.forEach(notificacion => {
              const li = document.createElement("li");
              li.innerHTML = `
          <strong>Para:</strong> ${notificacion.destinatario}<br>
          <strong>Mensaje:</strong> ${notificacion.contenido}
      `;
              feedbackList.appendChild(li);
          });
      })
      .catch(error => {
          alert("Hubo un error al cargar las notificaciones.");
      });
}

// Llama esta función cuando cargue la página
document.addEventListener("DOMContentLoaded", cargarNotificaciones);