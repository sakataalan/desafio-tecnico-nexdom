<script setup>
import AppSidebar from './AppSidebar.vue'
</script>

<template>
  <div class="flex h-screen bg-slate-50 font-sans">
    <AppSidebar />
    <main class="flex-1 overflow-x-hidden overflow-y-auto px-8 pb-8 pt-4">
      <slot />
    </main>
  </div>
</template>
