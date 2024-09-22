package com.example.idea.domain.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object Constants {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://kmpirqxxydrppeektizj.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImttcGlycXh4eWRycHBlZWt0aXpqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjYzMTcwOTAsImV4cCI6MjA0MTg5MzA5MH0.t5CQUsB0szBPj5Ydy7FL9CE0BllArx7IKpa-y0cxsxA"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime)
    }
}