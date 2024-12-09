
        const calendarContainer = document.getElementById('dates');
        const monthYear = document.getElementById('monthYear');
        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');

        // Sample events (could be fetched from a database)
        const events = [
            { date: '2024-10-27', event: 'Tarea 3', color: 'green' },
            { date: '2024-10-30', event: 'Tarea 2', color: 'blue' }
        ];

        let today = new Date();
        let currentMonth = today.getMonth();
        let currentYear = today.getFullYear();

        // Generate calendar for the current month and year
        function generateCalendar(month, year) {
            const firstDay = new Date(year, month).getDay();
            const daysInMonth = new Date(year, month + 1, 0).getDate();
            calendarContainer.innerHTML = '';

            monthYear.textContent = new Date(year, month).toLocaleString('es-ES', { month: 'long', year: 'numeric' });

            // Fill in blank days
            for (let i = 0; i < (firstDay + 6) % 7; i++) {
                const blank = document.createElement('div');
                blank.classList.add('date');
                calendarContainer.appendChild(blank);
            }

            // Generate the days
            for (let day = 1; day <= daysInMonth; day++) {
                const date = new Date(year, month, day);
                const dayElement = document.createElement('div');
                dayElement.classList.add('date');
                dayElement.textContent = day;

                if (day === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                    dayElement.classList.add('today'); // Highlight today
                }

                // Check if there is an event for this date
                const event = events.find(e => e.date === date.toISOString().slice(0, 10));
                if (event) {
                    const eventElement = document.createElement('div');
                    eventElement.classList.add('event', event.color);
                    eventElement.textContent = event.event;
                    dayElement.appendChild(eventElement);
                }

                calendarContainer.appendChild(dayElement);
            }
        }

        // Navigation (Prev/Next)
        prevButton.addEventListener('click', () => {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            generateCalendar(currentMonth, currentYear);
        });

        nextButton.addEventListener('click', () => {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            generateCalendar(currentMonth, currentYear);
        });

        // Initialize calendar
        generateCalendar(currentMonth, currentYear);
   

        