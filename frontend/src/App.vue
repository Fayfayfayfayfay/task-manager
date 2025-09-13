<template>
  <div class="task-manager">
    <h1 class="title">Task Manager</h1>

    <form class="task-form" @submit.prevent="addTask">
      <input v-model="newTitle" placeholder="Titel" required />
      <input v-model="newDescription" placeholder="Beschreibung" />
      <button type="submit" class="btn">Add</button>
    </form>

    <table class="task-table">
      <thead>
      <tr>
        <th>Titel</th>
        <th>Beschreibung</th>
        <th>Status</th>
        <th>Erstellungsdatum</th>
        <th>Aktionen</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="task in tasks" :key="task.id">
        <td>{{ task.title }}</td>
        <td>{{ task.description }}</td>
        <td>{{ task.status }}</td>
        <td>{{ formatDate(task.createdAt) }}</td>
        <td class="actions">
          <button class="btn" @click="setStatus(task, 'OPEN')">Open</button>
          <button class="btn" @click="setStatus(task, 'IN_PROGRESS')">In Progress</button>
          <button class="btn" @click="setStatus(task, 'DONE')">Done</button>
          <button class="btn btn-delete" @click="deleteTask(task)">🗑 Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"

const tasks = ref([])
const newTitle = ref("")
const newDescription = ref("")

const loadTasks = async () => {
  try {
    const res = await axios.get("http://localhost:8080/api/tasks")
    tasks.value = res.data
  } catch (err) {
    console.error("Backend nicht erreichbar", err)
  }
}

const addTask = async () => {
  if (!newTitle.value) return
  try {
    const res = await axios.post("http://localhost:8080/api/tasks", {
      title: newTitle.value,
      description: newDescription.value,
      status: "OPEN",
      createdAt: new Date().toISOString()
    })
    tasks.value.push(res.data)
    newTitle.value = ""
    newDescription.value = ""
  } catch (err) {
    console.error("Task konnte nicht erstellt werden", err)
  }
}

const setStatus = async (task, status) => {
  const oldStatus = task.status
  task.status = status
  try {
    await axios.put(`http://localhost:8080/api/tasks/${task.id}`, { ...task })
  } catch (err) {
    console.error("Status konnte nicht aktualisiert werden", err)
    task.status = oldStatus
  }
}

// Task löschen – UI sofort aktualisieren
const deleteTask = async (task) => {
  const index = tasks.value.findIndex(t => t.id === task.id)
  if (index === -1) return
  tasks.value.splice(index, 1) // Sofort UI aktualisieren
  try {
    await axios.delete(`http://localhost:8080/api/tasks/${task.id}`)
  } catch (err) {
    console.error("Task konnte nicht gelöscht werden", err)
    tasks.value.splice(index, 0, task) // Bei Fehler zurücksetzen
  }
}

// Datum formatieren
const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric"
  })
}

onMounted(loadTasks)
</script>
