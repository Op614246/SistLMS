document.getElementById("create-course-link").addEventListener("click", function () {
    document.getElementById("create-course-modal").style.display = "block";
    document.getElementById("overlay").style.display = "block";
});

document.querySelector(".close-btn").addEventListener("click", function () {
    document.getElementById("create-course-modal").style.display = "none";
    document.getElementById("overlay").style.display = "none";
});

 /*
document.getElementById("create-course-form").addEventListener("submit", function (e) {
   e.preventDefault();

   const courseName = document.getElementById("course-name").value;
   const courseImageFile = document.getElementById("course-image").files[0];

   if (courseName && courseImageFile) {
      const reader = new FileReader();
      reader.onload = function (event) {
         const newCourse = document.createElement("div");
         newCourse.classList.add("box");

         const thumbDiv = document.createElement("div");
         thumbDiv.classList.add("thumb");

         const courseImage = document.createElement("img");
         courseImage.src = event.target.result;
         courseImage.alt = courseName;
         thumbDiv.appendChild(courseImage);

         const courseTitle = document.createElement("h3");
         courseTitle.classList.add("title");
         courseTitle.textContent = courseName;

         const arrowBtn = document.createElement("a");
         arrowBtn.href = "secciones.html";
         arrowBtn.classList.add("arrow-btn");
         arrowBtn.innerHTML = '<i class="fas fa-arrow-right"></i>';

         newCourse.appendChild(thumbDiv);
         newCourse.appendChild(courseTitle);
         newCourse.appendChild(arrowBtn);

         document.querySelector(".box-container").appendChild(newCourse);

         // Cierra el modal
         document.getElementById("create-course-modal").style.display = "none";
         document.getElementById("overlay").style.display = "none";
      };
      reader.readAsDataURL(courseImageFile);
   }
});
 */